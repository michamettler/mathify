import { Routes } from '@angular/router';
import {
  UserRegistrationComponent
} from "./features/registration/components/user-registration/user-registration.component";
import {GradeSelectionComponent} from "./features/registration/components/grade-selection/grade-selection.component";

export const routes: Routes = [
  { path: '', component: UserRegistrationComponent},
  { path: 'login', component: UserRegistrationComponent},
  { path: 'grade-selection', component: GradeSelectionComponent}
]
