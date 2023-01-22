import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Post } from './post.model';



@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrls: ['./services.component.css']
})
export class ServicesComponent implements OnInit {

  selected: string = "";

  loadedpost: Post[] = [];

  newValue: string = "";

  displayedColumns: string[] = ['id', 'name', 'primarySkill', 'location', 'availability', 'proposedBy', 'source'];


  constructor(private http: HttpClient) {

  }

  ngOnInit() {


  }

  getProfile() {
    this.http.get<{ [key: string]: Post }>('http://localhost:8081/profiles')
      .pipe(map(responseData => {
        const arrayResponse = [];
        for (const key in responseData) {
          arrayResponse.push({ ...responseData[key] })
        }
        return arrayResponse;
      }))
      .subscribe(post => {
        console.log(post);
        this.loadedpost = post;

      });
  }

  getProfileBySearch() {

    this.http.get<{ [key: string]: Post }>('http://localhost:8081/profiles/by/search/' + this.newValue)
      .pipe(map(responseData => {
        const arrayResponse = [];
        for (const key in responseData) {
          arrayResponse.push({ ...responseData[key] })
        }
        return arrayResponse;
      }))
      .subscribe(post => {
        console.log(post);
        this.loadedpost = post;

      });
  }

  clearProfile() {
    this.loadedpost = [];
  }
}
