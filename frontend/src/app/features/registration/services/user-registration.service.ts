import {Injectable} from '@angular/core';
import {AuthService} from "../../../core/services/auth.service";
import {User} from "../../../../model/user";
import {HttpClient, HttpParams} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserRegistrationService {

  private apiUrl = 'https://localhost:8443';

  constructor(private http: HttpClient, private authService: AuthService) {
  }

  login(user: User): Observable<any> {
    let url = `${this.apiUrl}/login`;
    if (user.username && user.password) {
      let params = new HttpParams()
        .set('username', user.username)
        .set('password', user.password);

      return this.http.get(url, {params: params})
        .pipe(
          catchError(this.handleError('login'))
        );
    } else {
      throw new Error('User information missing!');
    }
  }

  register(user: User): Observable<any> {
    let url = `${this.apiUrl}/users`;
    if (user.username && user.password && user.email && user.grade) {
      return this.http.post(url, {
        username: user.username,
        password: user.password,
        email: user.email,
        grade: user.grade
      }).pipe(
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

  logout(): void {
    this.authService.logout();
  }
}
