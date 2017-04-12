import { Component, OnInit } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
    title = 'Users';
    users = [{firstName: "david", lastName: "ho"}, {firstName: "ve", lastName: "liou"}];

    constructor(private http: Http) {
        
    }

    ngOnInit() {
        this.http.get("http://localhost:8080/users/resources/users").toPromise().then(r => r.json()).then( r => this.users = r);
    }
}
