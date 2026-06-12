import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  private readonly accessTokenKey = 'devops_access_token';
  private readonly refreshTokenKey = 'devops_refresh_token';
  private readonly userKey = 'devops_user';

  saveSession(response: any): void {
    localStorage.setItem(this.accessTokenKey, response.accessToken);
    localStorage.setItem(this.refreshTokenKey, response.refreshToken);

    const user = {
      id: response.id,
      email: response.email,
      fullName: response.fullName,
      roles: response.roles
    };

    localStorage.setItem(this.userKey, JSON.stringify(user));
  }

  getAccessToken(): string | null {
    return localStorage.getItem(this.accessTokenKey);
  }

  getUser(): any | null {
    const user = localStorage.getItem(this.userKey);
    return user ? JSON.parse(user) : null;
  }

  isAuthenticated(): boolean {
    return !!this.getAccessToken();
  }

  clearSession(): void {
    localStorage.removeItem(this.accessTokenKey);
    localStorage.removeItem(this.refreshTokenKey);
    localStorage.removeItem(this.userKey);
  }
}
