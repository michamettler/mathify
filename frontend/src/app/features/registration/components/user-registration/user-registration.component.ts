import {Component} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
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

  passwordMismatch: boolean = false;

  form = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
    repeatPassword: new FormControl('', Validators.required)
  });

  constructor(private router: Router, private _snackBar: MatSnackBar,
              private userRegistrationService: UserRegistrationService,
              private titleService: Title) {
    this.titleService.setTitle('Registration');
  }

  register(): void {
    this.form.updateValueAndValidity();
    if (!this.checkPasswords()) {
      this.form.get('repeatPassword')?.setValue('');
      return;
    }

    if (this.form.valid) {
      this.userRegistrationService.register(this.form.get('passwordForm')?.value, this.form.get('passwordForm')?.value);
      this._snackBar.open("User has been created! You can now log in.", "dismiss", {
        verticalPosition: 'top',
        horizontalPosition: 'end'
      });
      this.router.navigate(['/login']);
    }
  }

  checkPasswords(): boolean {
    if (this.form.get('password')?.value !== this.form.get('repeatPassword')?.value) {
      this.passwordMismatch = true;
      return false;
    }
    this.passwordMismatch = false;
    return true;
  }

}
