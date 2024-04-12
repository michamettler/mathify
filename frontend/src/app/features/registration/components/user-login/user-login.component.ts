import {Component} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {MatError, MatFormField, MatFormFieldModule} from "@angular/material/form-field";
import {MatInput, MatInputModule} from "@angular/material/input";
import {MatSnackBar} from "@angular/material/snack-bar";
import {UserRegistrationService} from "../../services/user-registration.service";
import {Title} from "@angular/platform-browser";
import {User} from "../../../../../model/user";

@Component({
  selector: 'app-user-registration',
  standalone: true,
  imports: [
    FormsModule,
    MatFormField,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatInput,
    MatError,
    ReactiveFormsModule,
  ],
  templateUrl: './user-login.component.html',
  styleUrl: './user-login.component.scss'
})
export class UserLoginComponent {
  form = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
  });

  constructor(private router: Router, private _snackBar: MatSnackBar,
              private userRegistrationService: UserRegistrationService,
              private titleService: Title) {
    titleService.setTitle('Login');
  }

  login(): void {
    if(this.form.valid) {
      let user: User = {
        username: this.form.get('username')?.value,
        password: this.form.get('password')?.value,
      }

      if (this.userRegistrationService.login(user)) {
        this.router.navigate(['/grade-selection']);
      } else {
        this._snackBar.open("Login failed! Please try again.", "dismiss", {
          verticalPosition: 'top',
          horizontalPosition: 'end'
        });
      }
    }
  }

  register(): void {
    this.router.navigate(['/registration']);
  }

}
