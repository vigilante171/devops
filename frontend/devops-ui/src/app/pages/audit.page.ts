import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuditEvent, AuditService, AuditSummary } from '../core/services/audit.service';

@Component({
  selector: 'app-audit-page',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './audit.page.html',
  styleUrl: './audit.page.scss'
})
export class AuditPage implements OnInit {
  summary?: AuditSummary;
  events: AuditEvent[] = [];
  errorMessage = '';

  constructor(private auditService: AuditService) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.auditService.getSummary().subscribe({
      next: (data) => this.summary = data,
      error: () => this.errorMessage = 'Could not load audit summary.'
    });

    this.auditService.getEvents().subscribe({
      next: (data) => this.events = data,
      error: () => {}
    });
  }
}
