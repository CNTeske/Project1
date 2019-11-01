import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  currentlyLoggedIn = false;

  constructor(private router: Router, private httpClient: HttpClient) { }

  login(credentials: {username: string, password: string}):boolean {
    const loginCredentials = {
      username: credentials.username,
      password: credentials.password
    };
    const url = 'http://localhost:8000/Project1/session';
    this.httpClient.post(url, loginCredentials)
        .subscribe((data) => {
          var b = JSON.parse(JSON.stringify(data));
          if (b.role == 0){
           var valid=false;
          } else {
           var valid = true;
          }
          console.log(valid);
          if (valid) {
            this.currentlyLoggedIn = true;
            this.router.navigateByUrl('/main');
            return true;
          } else {
            return false;
          }
        });
        return false;
  }
}