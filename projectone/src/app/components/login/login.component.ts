import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';
import { tick } from '@angular/core/testing';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  inputUsername = '';
  inputPassword = '';

  invalidInput = false;
  authenticated = false;

  constructor(private loginService: LoginService) { }

  ngOnInit() {
  }

  submit() {
    const credentials = {
      username: this.inputUsername,
      password: this.inputPassword
    };
    if (this.loginService.login(credentials)) {
    } else {
      this.invalidInput = true;
    }
  }
}
