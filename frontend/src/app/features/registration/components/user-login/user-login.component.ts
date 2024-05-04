import {Component} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatSnackBar} from "@angular/material/snack-bar";
import {UserRegistrationService} from "../../services/user-registration.service";
import {Title} from "@angular/platform-browser";
import {User} from "../../../../../model/user";
import {NgIf} from "@angular/common";
import {MatOption} from "@angular/material/autocomplete";
import {MatSelect} from "@angular/material/select";
import {HttpClientModule, HttpResponse} from "@angular/common/http";
import {AuthService} from "../../../../core/services/auth.service";
import {MatButton} from "@angular/material/button";

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
    HttpClientModule,
    NgIf,
    MatOption,
    MatSelect,
    MatButton
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
              private titleService: Title,
              private authService: AuthService) {
    this.titleService.setTitle('Login');
  }

  login(): void {
    if (this.form.valid) {
      let user: User = {
        username: this.form.get('username')?.value,
        password: this.form.get('password')?.value,
      }

      this.userRegistrationService.login(user).subscribe({
        next: (response: HttpResponse<any>) => {
          const token = response.headers.get('Authorization');
          if (token !== null) {
            console.log('Login successful')
            this.authService.login(token);
            this.router.navigate(['/grade-mode-selection']);
          } else {
            this.handleError('');
          }
        },
        error: (error) => {
          this.handleError(error);
        }
      });
    }
  }

  handleError(error: string) {
    console.error('Login failed:', error);
    this._snackBar.open("Login failed! Please try again.\n" + error, "dismiss", {
      verticalPosition: 'top',
      horizontalPosition: 'end'
    });
  }

  register(): void {
    this.router.navigate(['/registration']);
  }

}
