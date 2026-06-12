import { Routes } from '@angular/router';

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
    loadComponent: () =>
      import('./pages/dashboard.page').then((m) => m.DashboardPage)
  },
  {
    path: '**',
    loadComponent: () =>
      import('./pages/not-found.page').then((m) => m.NotFoundPage)
  }
];
