import {Injectable} from '@angular/core';
import {AuthService} from "../../../core/services/auth.service";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class UserRegistrationService {

  constructor(private authService: AuthService) {
  }

  register(username: string, password: string): void {
    console.log('Register', username, password);
    //TODO send data to backend
  }

  login(username: string, password: string): boolean {
    console.log('Login', username, password);
    return !!(username && password && this.authService.login(username, password));
  }

  logout(): void {
    this.authService.logout();
  }
}
