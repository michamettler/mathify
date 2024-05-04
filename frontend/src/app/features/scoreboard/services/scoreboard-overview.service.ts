import {Injectable} from '@angular/core';
import {catchError, Observable, throwError} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ScoreboardOverviewService {

  private apiUrl = 'https://localhost/api';

  constructor(private http: HttpClient) {
  }

  getScoreboard(): Observable<any> {
    let url = `${this.apiUrl}scoreboard`;

    const headers = new HttpHeaders({
      'Authorization': localStorage.getItem('token')?.toString() + '',
    });
    return this.http.get(url, {headers: headers})
      .pipe(
        catchError(this.handleError('scoreboard'))
      );
  }

  private handleError(operation = 'operation') {
    return (error: any): Observable<any> => {
      console.error(`${operation} failed: ${error.message}`);
      return throwError(() => new Error(`${operation} failed: ${error.message}`));
    };
  }
}
