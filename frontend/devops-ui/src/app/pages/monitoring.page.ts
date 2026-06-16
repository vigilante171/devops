import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AlertResponse, MonitoringService, MonitoringSummary, ServiceHealth } from '../core/services/monitoring.service';

@Component({
  selector: 'app-monitoring-page',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './monitoring.page.html',
  styleUrl: './monitoring.page.scss'
})
export class MonitoringPage implements OnInit {
  summary?: MonitoringSummary;
  health: ServiceHealth[] = [];
  alerts: AlertResponse[] = [];
  errorMessage = '';

  constructor(private monitoringService: MonitoringService) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.monitoringService.getSummary().subscribe({
      next: (data) => this.summary = data,
      error: () => this.errorMessage = 'Could not load monitoring summary.'
    });

    this.monitoringService.getHealth().subscribe({
      next: (data) => this.health = data,
      error: () => {}
    });

    this.monitoringService.getAlerts().subscribe({
      next: (data) => this.alerts = data,
      error: () => {}
    });
  }

  resolveAlert(id: string): void {
    this.monitoringService.resolveAlert(id).subscribe({
      next: () => this.loadData(),
      error: () => this.errorMessage = 'Could not resolve alert.'
    });
  }
}
