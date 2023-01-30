import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ServicesComponent } from './services/services.component';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  { 'path': '', 'component': HomeComponent },
  { 'path': 'search', component: ServicesComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
