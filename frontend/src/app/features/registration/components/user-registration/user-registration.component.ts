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
    NgIf,
    MatOption,
    MatSelect
  ],
  templateUrl: './user-registration.component.html',
  styleUrl: './user-registration.component.scss'
})
export class UserRegistrationComponent {

  passwordMismatch: boolean = false;
  grades = [
    {name: 'Grade 1', number: 1},
    {name: 'Grade 2', number: 2},
    {name: 'Grade 3', number: 3},
  ];

  form = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
    repeatPassword: new FormControl('', Validators.required),
  });

  gradeControl = new FormControl('', Validators.required);

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
        grade: this.gradeControl.get('grade')?.value
      }
      this.userRegistrationService.register(user);
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
