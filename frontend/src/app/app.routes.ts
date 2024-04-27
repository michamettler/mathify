import {Routes} from '@angular/router';
import {UserLoginComponent} from "./features/registration/components/user-login/user-login.component";
import {ModeSelectionComponent} from "./features/registration/components/mode-selection/mode-selection.component";
import {AuthGuard} from "./core/guards/auth.guard";
import {
  ScoreboardOverviewComponent
} from "./features/scoreboard/components/scoreboard-overview/scoreboard-overview.component";
import {
  UserRegistrationComponent
} from "./features/registration/components/user-registration/user-registration.component";
import {
  MathExerciseViewComponent
} from "./features/exercise/components/math-exercise-view/math-exercise-view.component";

export const routes: Routes = [
  {path: '', component: UserLoginComponent},
  {path: 'login', component: UserLoginComponent},
  {path: 'registration', component: UserRegistrationComponent},
  {path: 'mode-selection', component: ModeSelectionComponent, canActivate: [AuthGuard]},
  {path: 'scoreboard', component: ScoreboardOverviewComponent, canActivate: [AuthGuard]},
  {path: 'exercise', component: MathExerciseViewComponent}
]
