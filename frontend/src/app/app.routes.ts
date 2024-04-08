import {Routes} from '@angular/router';
import {
  UserRegistrationComponent
} from "./features/registration/components/user-registration/user-registration.component";
import {GradeSelectionComponent} from "./features/registration/components/grade-selection/grade-selection.component";
import {AuthGuard} from "./core/guards/auth.guard";
import {
  ScoreboardOverviewComponent
} from "./features/scoreboard/components/scoreboard-overview/scoreboard-overview.component";

export const routes: Routes = [
  {path: '', component: UserRegistrationComponent},
  {path: 'login', component: UserRegistrationComponent},
  {path: 'grade-selection', component: GradeSelectionComponent, canActivate: [AuthGuard]},
  {path: 'scoreboard', component: ScoreboardOverviewComponent, canActivate: [AuthGuard]}
]
