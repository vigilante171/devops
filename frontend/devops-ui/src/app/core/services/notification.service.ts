import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from './api.config';

export interface NotificationSummary {
  total: number;
  unread: number;
  criticalUnread: number;
  warningUnread: number;
}

export interface NotificationResponse {
  id: string;
  recipientEmail: string;
  title: string;
  message: string;
  type: string;
  severity: string;
  status: string;
  channel: string;
  sourceService: string;
  projectId: string;
  entityId: string;
  actionUrl: string;
  createdAt: string;
}

@Injectable({ providedIn: 'root' })
export class NotificationService {
  private readonly apiUrl = `${API_BASE_URL}/api/notifications`;

  constructor(private http: HttpClient) {}

  getNotifications(): Observable<NotificationResponse[]> {
    return this.http.get<NotificationResponse[]>(this.apiUrl);
  }

  getSummary(): Observable<NotificationSummary> {
    return this.http.get<NotificationSummary>(`${this.apiUrl}/summary`);
  }

  createNotification(body: any): Observable<NotificationResponse> {
    return this.http.post<NotificationResponse>(this.apiUrl, body);
  }

  markRead(id: string): Observable<NotificationResponse> {
    return this.http.post<NotificationResponse>(`${this.apiUrl}/${id}/read`, {});
  }

  markAllRead(): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/read-all`, {});
  }
}
