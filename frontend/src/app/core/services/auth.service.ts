import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private isAuthenticated = false;
  private authSecretKey = '' ;

  constructor() {
    this.isAuthenticated = !!localStorage.getItem(this.authSecretKey);
  }

  login(username: string, password: string): boolean {
    //TODO call login sevice
    if (true) {
      localStorage.setItem(this.authSecretKey, 'test_token'); // TODO replace with token from backend
      return true;
    } else {
      return false;
    }
  }

  isAuthenticatedUser(): boolean {
    return !!localStorage.getItem(this.authSecretKey);
  }

  logout(): void {
    localStorage.removeItem(this.authSecretKey);
    this.isAuthenticated = false;
  }
}
