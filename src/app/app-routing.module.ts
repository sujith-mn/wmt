import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { SearchComponent } from './search/search.component';
import { AssignComponent } from './assign/assign.component';
import { LoginComponent } from './login/login.component';
import { AuthGuardService } from './shared/services/auth-guard.service';
import { AssignedProfilesComponent } from './assigned-profiles/assigned-profiles.component';

const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'home', component: HomeComponent,canActivate:[AuthGuardService]},
  {path: 'profile', component: ProfileComponent,canActivate:[AuthGuardService]},
  {path: 'demand', component: SearchComponent,canActivate:[AuthGuardService]},
  {path: 'assign', component: AssignComponent,canActivate:[AuthGuardService]},
  {path: 'assignedprofile/:id', component: AssignedProfilesComponent,canActivate:[AuthGuardService]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [AuthGuardService]
})
export class AppRoutingModule { }
