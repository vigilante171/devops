import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from './api.config';

export interface CreateDeploymentRequest {
  projectId: string;
  pipelineId?: string;
  pipelineRunId?: string;
  applicationName: string;
  version: string;
  artifactUrl?: string;
  environment: 'DEVELOPMENT' | 'TESTING' | 'STAGING' | 'PRODUCTION';
  strategy: 'STANDARD' | 'BLUE_GREEN' | 'CANARY' | 'ROLLING';
  notes?: string;
}

export interface DeploymentResponse {
  id: string;
  projectId: string;
  pipelineId: string;
  pipelineRunId: string;
  applicationName: string;
  version: string;
  artifactUrl: string;
  environment: string;
  strategy: string;
  status: string;
  deployedBy: string;
  notes: string;
  createdAt: string;
  updatedAt: string;
}

@Injectable({ providedIn: 'root' })
export class DeploymentService {
  private readonly apiUrl = `${API_BASE_URL}/api/deployments`;

  constructor(private http: HttpClient) {}

  getDeployments(): Observable<DeploymentResponse[]> {
    return this.http.get<DeploymentResponse[]>(this.apiUrl);
  }

  createDeployment(request: CreateDeploymentRequest): Observable<DeploymentResponse> {
    return this.http.post<DeploymentResponse>(this.apiUrl, request);
  }

  updateStatus(id: string, status: string): Observable<DeploymentResponse> {
    return this.http.patch<DeploymentResponse>(`${this.apiUrl}/${id}/status`, { status });
  }

  rollback(id: string, rollbackTargetVersion: string, reason: string): Observable<DeploymentResponse> {
    return this.http.post<DeploymentResponse>(`${this.apiUrl}/${id}/rollback`, {
      rollbackTargetVersion,
      reason
    });
  }
}
