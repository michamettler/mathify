import {Injectable} from '@angular/core';
import {User} from "../../../../model/user";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserRegistrationService {

  private apiUrl = 'https://localhost/api';

  constructor(private http: HttpClient) {
  }

  login(user: User): Observable<any> {
    let url = `${this.apiUrl}login`;
    if (user.username && user.password) {
      const authData = btoa(user.username + ':' + user.password);
      const headers = new HttpHeaders({
        'Authorization': 'Basic ' + authData
      });
      return this.http.get(url, {headers: headers, observe: 'response'})
        .pipe(
          catchError(this.handleError('login'))
        );
    } else {
      return throwError(() => new Error('User information missing!'));
    }
  }

  register(user: User): Observable<any> {
    let url = `${this.apiUrl}register`;
    if (user.username && user.password && user.email && user.grade) {
      return this.http.post(url, {
        username: user.username,
        password: user.password,
        email: user.email,
        grade: user.grade
      }, {responseType: 'text'}).pipe(
        catchError(this.handleError('register'))
      );
    } else {
      throw new Error('User information missing!');
    }
  }

  private handleError(operation = 'operation') {
    return (error: any): Observable<any> => {
      console.error(`${operation} failed: ${error.message}`);
      return throwError(() => new Error(`${operation} failed: ${error.message}`));
    };
  }

}
