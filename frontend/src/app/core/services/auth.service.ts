import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authSecretKey = 'token';

  constructor() {
  }

  login(token: string) {
    localStorage.setItem(this.authSecretKey, token);
  }

  isAuthenticatedUser(): boolean {
    return !!localStorage.getItem(this.authSecretKey);
  }

  logout(): void {
    localStorage.removeItem(this.authSecretKey);
  }
}
