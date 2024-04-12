import {Injectable} from '@angular/core';
import {AuthService} from "../../../core/services/auth.service";

@Injectable({
  providedIn: 'root'
})
export class UserRegistrationService {

  constructor(private authService: AuthService) {
  }

  register(username: string | undefined, password: string | undefined): boolean {
    console.log('Register', username, password);
    //TODO send data to backend
    return true;
  }

  login(username: string, password: string): boolean {
    console.log('Login', username, password);
    return !!(username && password && this.authService.login(username, password));
    //TODO send data to backend
  }

  logout(): void {
    this.authService.logout();
  }
}
