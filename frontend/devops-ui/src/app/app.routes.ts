import { Routes } from '@angular/router';
import { authGuard } from './core/auth/guards/auth.guard';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'login'
  },
  {
    path: 'login',
    loadComponent: () =>
      import('./pages/login.page').then((m) => m.LoginPage)
  },
  {
    path: 'dashboard',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/dashboard.page').then((m) => m.DashboardPage)
  },
  {
    path: 'projects',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/projects.page').then((m) => m.ProjectsPage)
  },
  {
    path: 'pipelines',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/pipelines.page').then((m) => m.PipelinesPage)
  },
  {
    path: 'deployments',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/deployments.page').then((m) => m.DeploymentsPage)
  },
  {
    path: 'monitoring',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/monitoring.page').then((m) => m.MonitoringPage)
  },
  {
    path: 'notifications',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/notifications.page').then((m) => m.NotificationsPage)
  },
  {
    path: 'audit',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/audit.page').then((m) => m.AuditPage)
  },
  {
    path: '**',
    loadComponent: () =>
      import('./pages/not-found.page').then((m) => m.NotFoundPage)
  }
];
