import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {User} from "../../../../model/user";
import {catchError, Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MathExerciseService {

  private apiUrl = 'https://localhost/api';

  constructor(private http: HttpClient) {
  }

  retrieveExercise(user: User, subtype: string): Observable<any> {
    let url = `${this.apiUrl}exercise`;

    const headers = new HttpHeaders({
      'Authorization': localStorage.getItem('token')?.toString() + '',
      'Content-Type': 'application/json'
    });

    const queryParameters = {
      exerciseSubType: subtype,
      grade: user.grade ? user.grade : ''
    };

    const httpParams = new HttpParams({fromObject: queryParameters});

    return this.http.get(url, {headers: headers, params: httpParams}).pipe(
      catchError(this.handleError('login'))
    );
  }

  private handleError(operation = 'operation') {
    return (error: any): Observable<any> => {
      console.error(`${operation} failed: ${error.message}`);
      return throwError(() => new Error(`${operation} failed: ${error.message}`));
    };
  }

}
