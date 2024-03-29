import {Component} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-registration',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './user-registration.component.html',
  styleUrl: './user-registration.component.scss'
})
export class UserRegistrationComponent {
  userName: string = '';
  password: string = '';

  constructor(private router: Router) {
  }

  login(): void {
    console.log('Login', this.userName, this.password);
    //TODO implement login logic here
    this.router.navigate(['/grade-selection']);
  }

  register(): void {
    //TODO implement register logic here
    console.log('Register', this.userName, this.password);
  }


}
