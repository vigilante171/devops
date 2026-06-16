import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

import { TokenService } from '../core/auth/services/token.service';
import { ProjectService, ProjectResponse } from '../core/services/project.service';
import { PipelineResponse, PipelineRunResponse, PipelineService } from '../core/services/pipeline.service';
import { DeploymentResponse, DeploymentService } from '../core/services/deployment.service';
import { AlertResponse, MonitoringService, MonitoringSummary, ServiceHealth } from '../core/services/monitoring.service';
import { NotificationService, NotificationSummary } from '../core/services/notification.service';
import { AuditEvent, AuditService } from '../core/services/audit.service';

@Component({
  selector: 'app-dashboard-page',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.page.html',
  styleUrl: './dashboard.page.scss'
})
export class DashboardPage implements OnInit {
  user: any;

  projects: ProjectResponse[] = [];
  pipelines: PipelineResponse[] = [];
  runs: PipelineRunResponse[] = [];
  deployments: DeploymentResponse[] = [];
  health: ServiceHealth[] = [];
  alerts: AlertResponse[] = [];
  auditEvents: AuditEvent[] = [];

  monitoringSummary?: MonitoringSummary;
  notificationSummary?: NotificationSummary;

  totalProjects = 0;
  activePipelines = 0;
  totalDeployments = 0;
  openAlerts = 0;
  productionHealth = 98.7;

  isLoading = false;

  constructor(
    private tokenService: TokenService,
    private router: Router,
    private projectService: ProjectService,
    private pipelineService: PipelineService,
    private deploymentService: DeploymentService,
    private monitoringService: MonitoringService,
    private notificationService: NotificationService,
    private auditService: AuditService,
    private cdr: ChangeDetectorRef
  ) {
    this.user = this.tokenService.getUser();
  }

  ngOnInit(): void {
    setTimeout(() => this.loadDashboard(), 150);
  }

  loadDashboard(): void {
    this.isLoading = true;

    this.projectService.getProjects().subscribe({
      next: (data) => {
        this.projects = data;
        this.totalProjects = data.length;
        this.cdr.detectChanges();
      },
      error: () => {}
    });

    this.pipelineService.getPipelines().subscribe({
      next: (data) => {
        this.pipelines = data;
        this.activePipelines = data.filter((pipeline) => pipeline.status === 'ACTIVE').length;
        this.cdr.detectChanges();
      },
      error: () => {}
    });

    this.pipelineService.getRuns().subscribe({
      next: (data) => {
        this.runs = data.slice(0, 4);
      },
      error: () => {}
    });

    this.deploymentService.getDeployments().subscribe({
      next: (data) => {
        this.deployments = data.slice(0, 4);
        this.totalDeployments = data.length;
        this.cdr.detectChanges();
      },
      error: () => {}
    });

    this.monitoringService.getSummary().subscribe({
      next: (data) => {
        this.monitoringSummary = data;
        this.openAlerts = data.openAlerts;
        this.productionHealth = data.totalServices > 0
          ? Math.round((data.healthyServices / data.totalServices) * 1000) / 10
          : 98.7;
        this.cdr.detectChanges();
      },
      error: () => {}
    });

    this.monitoringService.getHealth().subscribe({
      next: (data) => {
        this.health = data.slice(0, 5);
      },
      error: () => {}
    });

    this.monitoringService.getAlerts().subscribe({
      next: (data) => {
        this.alerts = data.filter((alert) => alert.status !== 'RESOLVED').slice(0, 4);
      },
      error: () => {}
    });

    this.notificationService.getSummary().subscribe({
      next: (data) => {
        this.notificationSummary = data;
      },
      error: () => {}
    });

    this.auditService.getEvents().subscribe({
      next: (data) => {
        this.auditEvents = data.slice(0, 4);
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
      }
    });
  }

  logout(): void {
    this.tokenService.clearSession();
    this.router.navigateByUrl('/login');
  }
}

