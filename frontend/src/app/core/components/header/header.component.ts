import {Component, Input} from '@angular/core';
import {NgIf} from "@angular/common";
import {UserRegistrationService} from "../../../features/registration/services/user-registration.service";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  @Input() gradeSelected: boolean = false;

  constructor(private userRegistrationService: UserRegistrationService) {
  }

  logout() {
    this.userRegistrationService.logout();
  }
}
