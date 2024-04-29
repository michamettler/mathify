import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";
import {Exercise} from "../../../../model/exercise";

@Injectable({
  providedIn: 'root'
})
export class MathExerciseService {

  private apiUrl = 'https://localhost/api';

  constructor(private http: HttpClient) {
  }

  retrieveExercise(): Observable<any> {
    let url = `${this.apiUrl}exercise`;

    const headers = new HttpHeaders({
      'Authorization': localStorage.getItem('token')?.toString() + '',
    });
    return this.http.get(url, {headers: headers})
      .pipe(
        catchError(this.handleError('login'))
      );
  }

  verifyExercise(exercise: Exercise) {
    let url = `${this.apiUrl}exercise/verify`;

    const headers = new HttpHeaders({
      'Authorization': localStorage.getItem('token')?.toString() + '',
    });
    return this.http.post(url, exercise, {headers: headers}).pipe(
      catchError(this.handleError('register'))
    );
  }

  private handleError(operation = 'operation') {
    return (error: any): Observable<any> => {
      console.error(`${operation} failed: ${error.message}`);
      return throwError(() => new Error(`${operation} failed: ${error.message}`));
    };
  }

}
