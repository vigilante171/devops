import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NotificationResponse, NotificationService, NotificationSummary } from '../core/services/notification.service';

@Component({
  selector: 'app-notifications-page',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './notifications.page.html',
  styleUrl: './notifications.page.scss'
})
export class NotificationsPage implements OnInit {
  summary?: NotificationSummary;
  notifications: NotificationResponse[] = [];
  errorMessage = '';

  constructor(private notificationService: NotificationService) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.notificationService.getSummary().subscribe({
      next: (data) => this.summary = data,
      error: () => this.errorMessage = 'Could not load notification summary.'
    });

    this.notificationService.getNotifications().subscribe({
      next: (data) => this.notifications = data,
      error: () => {}
    });
  }

  markRead(id: string): void {
    this.notificationService.markRead(id).subscribe({
      next: () => this.loadData(),
      error: () => this.errorMessage = 'Could not mark notification as read.'
    });
  }

  markAllRead(): void {
    this.notificationService.markAllRead().subscribe({
      next: () => this.loadData(),
      error: () => this.errorMessage = 'Could not mark all notifications as read.'
    });
  }
}
