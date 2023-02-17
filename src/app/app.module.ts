import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HomeComponent } from './home/home.component';
import { HttpClientModule } from '@angular/common/http';
import { SearchComponent } from './search/search.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSortModule} from '@angular/material/sort';
import { MatDialogModule } from "@angular/material/dialog";
import { MatCardModule } from "@angular/material/card";
import { MatButtonModule } from "@angular/material/button";
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatRippleModule } from '@angular/material/core';
import {MatTableModule} from '@angular/material/table'
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { FormsModule ,ReactiveFormsModule} from '@angular/forms';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatIconModule} from '@angular/material/icon';
import { NavbarComponent } from './navbar/navbar.component';
import { ProfileComponent } from './profile/profile.component'
import { DataStorageService } from './shared/services/data-storage.service';
import { APP_CONFIG } from './app-config';
import { environment } from 'src/environments/environment.prod';
import { NotificationService } from './shared/services/notification.service';
import { AssignComponent } from './assign/assign.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SearchComponent,
    NavbarComponent,
    ProfileComponent,
    AssignComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    FormsModule,
    MatInputModule,
    ReactiveFormsModule,
    MatPaginatorModule,
    MatSortModule,
    MatDialogModule,
      MatCardModule,
      MatButtonModule,
      MatButtonModule,
    MatFormFieldModule,
    MatRippleModule,
    MatTableModule,
    MatIconModule,
    MatToolbarModule,
    MatSnackBarModule
  ],
  exports: [
    MatButtonModule,
    MatFormFieldModule,
    MatRippleModule,
  ],
  providers: [DataStorageService,NotificationService,{ provide: APP_CONFIG, useValue: environment }],
  bootstrap: [AppComponent]
})
export class AppModule { }
