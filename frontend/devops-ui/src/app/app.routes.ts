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
    path: '**',
    loadComponent: () =>
      import('./pages/not-found.page').then((m) => m.NotFoundPage)
  }
];
