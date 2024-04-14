import {Component, Input} from '@angular/core';
import {NgIf} from "@angular/common";
import {AuthService} from "../../services/auth.service";

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

  constructor(private authService: AuthService) {
  }

  logout() {
    this.authService.logout();
  }
}
