import {Component} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {Router} from "@angular/router";
import {MatFormField, MatFormFieldModule} from "@angular/material/form-field";
import {MatInput, MatInputModule} from "@angular/material/input";
import {MatSnackBar} from "@angular/material/snack-bar";

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

  constructor(private router: Router, private _snackBar: MatSnackBar) {
  }

  login(): void {
    console.log('Login', this.userName, this.password);
    //TODO send data to backend
    if (this.userName && this.password) {
      this.router.navigate(['/grade-selection']);
    }
  }

  register(): void {
    console.log('Register', this.userName, this.password);
    //TODO send data to backend
    this._snackBar.open("User has been created! You can now log in.", "dismiss", {verticalPosition: 'top', horizontalPosition: 'end'});
  }

}
