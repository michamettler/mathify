import {Component} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {UserRegistrationService} from "../../services/user-registration.service";
import {Title} from "@angular/platform-browser";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-user-registration',
  standalone: true,
  imports: [
    FormsModule,
    MatFormField,
    MatInput,
    MatLabel,
    MatError,
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './user-registration.component.html',
  styleUrl: './user-registration.component.scss'
})
export class UserRegistrationComponent {
  userName: string = '';
  password: string = '';
  repeatPassword: string = '';
  email: string = '';

  passwordsDoNotMatch: boolean = false;

  constructor(private router: Router, private _snackBar: MatSnackBar,
              private userRegistrationService: UserRegistrationService,
              private titleService: Title) {
    this.titleService.setTitle('Registration');
  }

  register(): void {
    if (this.password === this.repeatPassword) {
      this.userRegistrationService.register(this.userName, this.password);
      this._snackBar.open("User has been created! You can now log in.", "dismiss", {
        verticalPosition: 'top',
        horizontalPosition: 'end'
      });
      this.router.navigate(['/login']);
    } else {
      this.repeatPassword = '';
      this.passwordsDoNotMatch = true;
    }
  }
}
