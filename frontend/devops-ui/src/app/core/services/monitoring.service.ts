import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from './api.config';

export interface MonitoringSummary {
  totalServices: number;
  healthyServices: number;
  degradedServices: number;
  downServices: number;
  openAlerts: number;
  criticalOpenAlerts: number;
}

export interface ServiceHealth {
  id: string;
  serviceName: string;
  serviceUrl: string;
  status: string;
  responseTimeMs: number;
  version: string;
  message: string;
  lastCheckedAt: string;
}

export interface AlertResponse {
  id: string;
  projectId: string;
  serviceName: string;
  title: string;
  description: string;
  severity: string;
  status: string;
  createdBy: string;
  createdAt: string;
}

@Injectable({ providedIn: 'root' })
export class MonitoringService {
  private readonly apiUrl = `${API_BASE_URL}/api/monitoring`;

  constructor(private http: HttpClient) {}

  getSummary(): Observable<MonitoringSummary> {
    return this.http.get<MonitoringSummary>(`${this.apiUrl}/summary`);
  }

  getHealth(): Observable<ServiceHealth[]> {
    return this.http.get<ServiceHealth[]>(`${this.apiUrl}/health`);
  }

  getAlerts(): Observable<AlertResponse[]> {
    return this.http.get<AlertResponse[]>(`${this.apiUrl}/alerts`);
  }

  createAlert(body: any): Observable<AlertResponse> {
    return this.http.post<AlertResponse>(`${this.apiUrl}/alerts`, body);
  }

  acknowledgeAlert(id: string): Observable<AlertResponse> {
    return this.http.post<AlertResponse>(`${this.apiUrl}/alerts/${id}/acknowledge`, {});
  }

  resolveAlert(id: string): Observable<AlertResponse> {
    return this.http.post<AlertResponse>(`${this.apiUrl}/alerts/${id}/resolve`, {});
  }
}
