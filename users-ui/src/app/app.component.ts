import { Component, OnInit } from '@angular/core';
import { Http } from '@angular/http';
import { FormsModule } from "@angular/forms";
import { NgModule }      from '@angular/core';
import { Headers } from '@angular/http';
import 'rxjs/add/operator/toPromise';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
    user = new User();
    search = new Search();
    users;

    constructor(private http: Http) {
    }

    ngOnInit() {
        this.http.get("http://localhost:8080/users/resources/users").toPromise()
            .then(r => r.json())
            .then( r => this.users = r);
    }

    submit() {
        const headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Accept', 'application/json');
        this.http.post("http://localhost:8080/users/resources/users", this.user, headers).toPromise()
            .then(r => r.json())
            .then( r => this.users = r);
    }

    searchUsers() {
        const headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Accept', 'application/json');
        this.http.post("http://localhost:8080/users/resources/users", this.search, headers).toPromise()
            .then(r => r.json())
            .then( r => this.users = r);
    }
}

@NgModule({
    declarations: [AppComponent],
    imports: [FormsModule],
    bootstrap: [AppComponent]
})

export class User {

    constructor(public first:string = "", public last:string = "", public dob:Date = new Date()) {
        
    }
}

export class Search {

    constructor(public searchstring:string = "") {
        
    }
}