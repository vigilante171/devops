$ErrorActionPreference = "Stop"

$baseUrl = "http://localhost:8080"

function Write-Step($message) {
  Write-Host ""
  Write-Host "==================================================" -ForegroundColor DarkGray
  Write-Host $message -ForegroundColor Cyan
  Write-Host "==================================================" -ForegroundColor DarkGray
}

function Invoke-Api($method, $uri, $body = $null, $token = $null) {
  $headers = @{}

  if ($token) {
    $headers["Authorization"] = "Bearer $token"
  }

  if ($body -ne $null) {
    return Invoke-RestMethod `
      -Method $method `
      -Uri $uri `
      -ContentType "application/json" `
      -Headers $headers `
      -Body ($body | ConvertTo-Json -Depth 20)
  }

  return Invoke-RestMethod `
    -Method $method `
    -Uri $uri `
    -Headers $headers
}

Write-Step "1. Testing public ping endpoints"

$pingEndpoints = @(
  "/api/auth/ping",
  "/api/projects/ping",
  "/api/pipelines/ping",
  "/api/deployments/ping",
  "/api/monitoring/ping",
  "/api/notifications/ping",
  "/api/audit/ping"
)

foreach ($endpoint in $pingEndpoints) {
  Write-Host "Testing $endpoint ..." -ForegroundColor Yellow
  Invoke-Api "GET" "$baseUrl$endpoint" | Format-Table
}

Write-Step "2. Login"

$loginBody = @{
  email = "admin@devops.com"
  password = "Admin@123456"
}

try {
  $login = Invoke-Api "POST" "$baseUrl/api/auth/login" $loginBody
}
catch {
  Write-Host "Login failed. Trying register first..." -ForegroundColor Yellow

  $registerBody = @{
    firstName = "Admin"
    lastName = "DevOps"
    email = "admin@devops.com"
    password = "Admin@123456"
  }

  Invoke-Api "POST" "$baseUrl/api/auth/register" $registerBody | Out-Null
  $login = Invoke-Api "POST" "$baseUrl/api/auth/login" $loginBody
}

$token = $login.accessToken

if (-not $token) {
  throw "No access token received from auth-service"
}

Write-Host "Login OK. Token received." -ForegroundColor Green

Write-Step "3. Testing authenticated /me"

Invoke-Api "GET" "$baseUrl/api/auth/me" $null $token | Format-List

Write-Step "4. Creating project"

$projectBody = @{
  name = "Backend Full Test Project"
  description = "Project created by automated backend test script"
  repository = @{
    provider = "GITHUB"
    repositoryUrl = "https://github.com/demo/backend-full-test"
    defaultBranch = "main"
    webhookEnabled = $true
  }
  environments = @(
    @{
      name = "Development"
      type = "DEVELOPMENT"
      baseUrl = "http://dev.local"
      protectedEnvironment = $false
    },
    @{
      name = "Staging"
      type = "STAGING"
      baseUrl = "http://staging.local"
      protectedEnvironment = $false
    },
    @{
      name = "Production"
      type = "PRODUCTION"
      baseUrl = "http://prod.local"
      protectedEnvironment = $true
    }
  )
}

$project = Invoke-Api "POST" "$baseUrl/api/projects" $projectBody $token
$projectId = $project.id

Write-Host "Project created: $projectId" -ForegroundColor Green

Write-Step "5. Creating pipeline"

$pipelineBody = @{
  projectId = $projectId
  name = "backend-ci-cd"
  description = "Build, test, scan and deploy pipeline"
  branch = "main"
  steps = @(
    @{
      name = "Checkout"
      command = "git checkout main"
      orderIndex = 1
    },
    @{
      name = "Build"
      command = "mvn clean package"
      orderIndex = 2
    },
    @{
      name = "Test"
      command = "mvn test"
      orderIndex = 3
    },
    @{
      name = "Deploy"
      command = "deploy artifact"
      orderIndex = 4
    }
  )
}

$pipeline = Invoke-Api "POST" "$baseUrl/api/pipelines" $pipelineBody $token
$pipelineId = $pipeline.id

Write-Host "Pipeline created: $pipelineId" -ForegroundColor Green

Write-Step "6. Running pipeline"

$runBody = @{
  branch = "main"
  commitSha = "abc123devops"
}

$run = Invoke-Api "POST" "$baseUrl/api/pipelines/$pipelineId/runs" $runBody $token
$runId = $run.id

Write-Host "Pipeline run created: $runId" -ForegroundColor Green

Write-Step "7. Completing pipeline run"

Invoke-Api "POST" "$baseUrl/api/pipelines/runs/$runId/complete?success=true" $null $token | Format-List

Write-Step "8. Creating deployment"

$deploymentBody = @{
  projectId = $projectId
  pipelineId = $pipelineId
  pipelineRunId = $runId
  applicationName = "backend-api"
  version = "v1.0.0"
  artifactUrl = "https://example.com/artifacts/backend-api-v1.jar"
  environment = "STAGING"
  strategy = "ROLLING"
  notes = "Automated test deployment"
}

$deployment = Invoke-Api "POST" "$baseUrl/api/deployments" $deploymentBody $token
$deploymentId = $deployment.id

Write-Host "Deployment created: $deploymentId" -ForegroundColor Green

Write-Step "9. Updating deployment status"

$statusBody = @{
  status = "SUCCESS"
  notes = "Deployment completed successfully"
}

Invoke-Api "PATCH" "$baseUrl/api/deployments/$deploymentId/status" $statusBody $token | Format-List

Write-Step "10. Creating monitoring health record"

$healthBody = @{
  serviceName = "auth-service"
  serviceUrl = "http://localhost:8081"
  status = "HEALTHY"
  responseTimeMs = 35
  version = "1.0.0"
  message = "Auth service operational"
}

Invoke-Api "POST" "$baseUrl/api/monitoring/health" $healthBody $token | Format-List

Write-Step "11. Creating alert"

$alertBody = @{
  projectId = $projectId
  serviceName = "monitoring-service"
  title = "Test alert"
  description = "Alert generated by backend test script"
  severity = "HIGH"
}

$alert = Invoke-Api "POST" "$baseUrl/api/monitoring/alerts" $alertBody $token
$alertId = $alert.id

Write-Host "Alert created: $alertId" -ForegroundColor Green

Write-Step "12. Acknowledging and resolving alert"

Invoke-Api "POST" "$baseUrl/api/monitoring/alerts/$alertId/acknowledge" $null $token | Format-List
Invoke-Api "POST" "$baseUrl/api/monitoring/alerts/$alertId/resolve" $null $token | Format-List

Write-Step "13. Creating notification"

$notificationBody = @{
  title = "Backend test completed"
  message = "The backend automated test flow is running successfully."
  type = "SYSTEM"
  severity = "SUCCESS"
  channel = "IN_APP"
  sourceService = "test-script"
  projectId = $projectId
  entityId = $deploymentId
  actionUrl = "/dashboard"
}

$notification = Invoke-Api "POST" "$baseUrl/api/notifications" $notificationBody $token
$notificationId = $notification.id

Write-Host "Notification created: $notificationId" -ForegroundColor Green

Write-Step "14. Marking notification as read"

Invoke-Api "POST" "$baseUrl/api/notifications/$notificationId/read" $null $token | Format-List

Write-Step "15. Creating audit event"

$auditBody = @{
  action = "DEPLOY"
  severity = "INFO"
  entityType = "DEPLOYMENT"
  entityId = $deploymentId
  serviceName = "deployment-service"
  message = "Automated backend test deployment completed."
  ipAddress = "127.0.0.1"
  userAgent = "PowerShell Backend Test"
  metadata = @{
    projectId = $projectId
    pipelineId = $pipelineId
    runId = $runId
    environment = "STAGING"
    version = "v1.0.0"
  }
}

Invoke-Api "POST" "$baseUrl/api/audit/events" $auditBody $token | Format-List

Write-Step "16. Reading summaries"

Write-Host "Monitoring summary:" -ForegroundColor Yellow
Invoke-Api "GET" "$baseUrl/api/monitoring/summary" $null $token | Format-List

Write-Host "Notification summary:" -ForegroundColor Yellow
Invoke-Api "GET" "$baseUrl/api/notifications/summary" $null $token | Format-List

Write-Host "Audit summary:" -ForegroundColor Yellow
Invoke-Api "GET" "$baseUrl/api/audit/summary" $null $token | Format-List

Write-Step "BACKEND FULL TEST COMPLETED SUCCESSFULLY"

Write-Host "All backend services are reachable through API Gateway." -ForegroundColor Green
Write-Host "Auth, Projects, Pipelines, Deployments, Monitoring, Notifications and Audit are working." -ForegroundColor Green
