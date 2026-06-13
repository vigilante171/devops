import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { TokenService } from '../services/token.service';

export const authGuard: CanActivateFn = () => {
  const tokenService = inject(TokenService);
  const router = inject(Router);

  const token = tokenService.getAccessToken();

  console.log('AuthGuard token:', token);

  if (token && token.length > 20) {
    return true;
  }

  return router.createUrlTree(['/login']);
};
