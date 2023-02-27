import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { login } from '../shared/model/login';
import { LoginService } from '../shared/services/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  constructor(private loginService: LoginService,
    private fb: FormBuilder, private router: Router) {}

    loginForm: FormGroup = this.fb.group({
      username: [null, [Validators.required]],
      password:[null,[ Validators.required]],
    });

  ngOnInit(): void {

  }

  onSubmit(){
    this.loginService.login(this.loginForm.value).subscribe(
      {
        next: (result: any) => {
          localStorage.setItem('loggedUser',this.loginForm.value.username);
          this.router.navigate(['/home']);
        },
        error: (err: any) => {
          //this.notificationService.setErrorMsg(err.error)
          console.log(err);
        },
        complete: () => {
          console.log('complete');
        }
      })
  }

}
