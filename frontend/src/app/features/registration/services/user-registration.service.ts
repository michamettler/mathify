import {Injectable} from '@angular/core';
import {AuthService} from "../../../core/services/auth.service";
import {FormControl, ɵFormGroupRawValue, ɵGetProperty, ɵTypedOrUntyped} from "@angular/forms";
import {User} from "../../../../model/user";

@Injectable({
  providedIn: 'root'
})
export class UserRegistrationService {

  constructor(private authService: AuthService) {
  }

  register(user: User): boolean {
    //TODO send data to backend
    return true;
  }

  login(user: User): boolean {
    //TODO retrieve token from backend
    return !!(user.username && user.password && this.authService.login(user.username, user.password));
  }

  logout(): void {
    this.authService.logout();
  }
}
