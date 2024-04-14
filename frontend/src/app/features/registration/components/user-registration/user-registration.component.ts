import {Component} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {UserRegistrationService} from "../../services/user-registration.service";
import {Title} from "@angular/platform-browser";
import {NgIf} from "@angular/common";
import {MatOption} from "@angular/material/autocomplete";
import {MatSelect} from "@angular/material/select";
import {User} from "../../../../../model/user";
import {Grade} from "../../../../../model/grade";
import {HttpClientModule} from "@angular/common/http";

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
    MatSelect
  ],
  templateUrl: './user-registration.component.html',
  styleUrl: './user-registration.component.scss'
})
export class UserRegistrationComponent {

  passwordMismatch: boolean = false;
  grades: Grade[] = [
    {title: 'Grade 1', value: 'first'},
    {title: 'Grade 2', value: 'second'},
    {title: 'Grade 3', value: 'third'},
  ];

  form = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
    repeatPassword: new FormControl('', Validators.required),
  });

  gradeControl: FormControl<Grade | null> = new FormControl<Grade | null>(null, Validators.required);

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
      let user: User = {
        username: this.form.get('username')?.value,
        password: this.form.get('password')?.value,
        email: this.form.get('email')?.value,
        grade: this.gradeControl.value?.value
      }

      this.userRegistrationService.register(user).subscribe({
        next: (response) => {
          console.log('Login successful', response);
          this._snackBar.open("User has been created! You can now log in.", "dismiss", {
            verticalPosition: 'top',
            horizontalPosition: 'end'
          });
          this.router.navigate(['/login']);
        },
        error: (error) => {
          console.error('Login failed:', error);
          this._snackBar.open("Registration failed!\n" + error, "dismiss", {
            verticalPosition: 'top',
            horizontalPosition: 'end'
          });
        }
      });

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
