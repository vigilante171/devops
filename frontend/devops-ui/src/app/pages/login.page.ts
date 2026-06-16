import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from '../core/auth/services/auth.service';
import { TokenService } from '../core/auth/services/token.service';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.page.html',
  styleUrl: './login.page.scss'
})
export class LoginPage {
  email = 'admin@devops.com';
  password = 'Admin@123456';

  isLoading = false;
  errorMessage = '';
  successMessage = '';

  constructor(
    private authService: AuthService,
    private tokenService: TokenService,
    private router: Router
  ) {}

  login(): void {
    this.errorMessage = '';
    this.successMessage = '';
    this.isLoading = true;

    console.log('Trying login with:', this.email);

    this.authService.login({
      email: this.email,
      password: this.password
    }).subscribe({
      next: async (response) => {
        console.log('Login success:', response);

        this.tokenService.saveSession(response);

        console.log('Saved token:', this.tokenService.getAccessToken());

        this.successMessage = 'Login successful. Redirecting...';
        this.isLoading = false;

        await this.router.navigate(['/dashboard']);
      },
      error: (error) => {
        console.error('Login failed:', error);

        this.isLoading = false;
        this.errorMessage =
          error?.error?.error ||
          error?.error?.message ||
          'Login failed. Please check your email and password.';
      }
    });
  }
}

