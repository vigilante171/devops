import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from './api.config';

export interface AuditSummary {
  totalEvents: number;
  infoEvents: number;
  lowEvents: number;
  mediumEvents: number;
  highEvents: number;
  criticalEvents: number;
  loginEvents: number;
  deploymentEvents: number;
  rollbackEvents: number;
  pipelineRunEvents: number;
}

export interface AuditEvent {
  id: string;
  actorEmail: string;
  action: string;
  severity: string;
  entityType: string;
  entityId: string;
  serviceName: string;
  message: string;
  ipAddress: string;
  userAgent: string;
  createdAt: string;
}

@Injectable({ providedIn: 'root' })
export class AuditService {
  private readonly apiUrl = `${API_BASE_URL}/api/audit`;

  constructor(private http: HttpClient) {}

  getEvents(): Observable<AuditEvent[]> {
    return this.http.get<AuditEvent[]>(`${this.apiUrl}/events`);
  }

  getMyEvents(): Observable<AuditEvent[]> {
    return this.http.get<AuditEvent[]>(`${this.apiUrl}/events/me`);
  }

  getSummary(): Observable<AuditSummary> {
    return this.http.get<AuditSummary>(`${this.apiUrl}/summary`);
  }

  createEvent(body: any): Observable<AuditEvent> {
    return this.http.post<AuditEvent>(`${this.apiUrl}/events`, body);
  }
}
