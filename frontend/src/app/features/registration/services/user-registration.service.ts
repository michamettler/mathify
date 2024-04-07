import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserRegistrationService {

  constructor() {
  }

  register(username: string, password: string): void {
    console.log('Register', username, password);
    //TODO send data to backend
  }
}
