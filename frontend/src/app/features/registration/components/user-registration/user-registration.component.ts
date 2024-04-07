import {Component} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {Router} from "@angular/router";
import {MatFormField, MatFormFieldModule} from "@angular/material/form-field";
import {MatInput, MatInputModule} from "@angular/material/input";
import {MatSnackBar} from "@angular/material/snack-bar";
import {UserRegistrationService} from "../../services/user-registration.service";

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
  templateUrl: './user-registration.component.html',
  styleUrl: './user-registration.component.scss'
})
export class UserRegistrationComponent {
  userName: string = '';
  password: string = '';

  constructor(private router: Router, private _snackBar: MatSnackBar,
              private userRegistrationService: UserRegistrationService) {
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
