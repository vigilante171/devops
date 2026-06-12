import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { TokenService } from '../core/auth/services/token.service';

@Component({
  selector: 'app-dashboard-page',
  standalone: true,
  templateUrl: './dashboard.page.html',
  styleUrl: './dashboard.page.scss'
})
export class DashboardPage {
  user: any;

  constructor(
    private tokenService: TokenService,
    private router: Router
  ) {
    this.user = this.tokenService.getUser();
  }

  logout(): void {
    this.tokenService.clearSession();
    this.router.navigateByUrl('/login');
  }
}
