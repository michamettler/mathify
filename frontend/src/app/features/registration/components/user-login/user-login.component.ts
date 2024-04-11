import {Component} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {Router} from "@angular/router";
import {MatFormField, MatFormFieldModule} from "@angular/material/form-field";
import {MatInput, MatInputModule} from "@angular/material/input";
import {MatSnackBar} from "@angular/material/snack-bar";
import {UserRegistrationService} from "../../services/user-registration.service";
import {Title} from "@angular/platform-browser";

@Component({
  selector: 'app-user-registration',
  standalone: true,
  imports: [
    FormsModule,
    MatFormField,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatInput
  ],
  templateUrl: './user-login.component.html',
  styleUrl: './user-login.component.scss'
})
export class UserLoginComponent {
  userName: string = '';
  password: string = '';

  constructor(private router: Router, private _snackBar: MatSnackBar,
              private userRegistrationService: UserRegistrationService,
              private titleService: Title) {
    titleService.setTitle('Login');
  }

  login(): void {
    console.log('Login', this.userName, this.password);
    if (this.userRegistrationService.login(this.userName, this.password)) {
      this.router.navigate(['/grade-selection']);
    } else {
      this.userName = '';
      this.password = '';
      this._snackBar.open("Login failed! Please try again.", "dismiss", {
        verticalPosition: 'top',
        horizontalPosition: 'end'
      });
    }
  }

  register(): void {
    console.log('Register', this.userName, this.password);
    this.userRegistrationService.register(this.userName, this.password);
    this._snackBar.open("User has been created! You can now log in.", "dismiss", {
      verticalPosition: 'top',
      horizontalPosition: 'end'
    });
  }

}
