import {async, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {AppComponent} from './app.component';
import { AccordionModule } from "primeng/accordion";
import { PanelModule } from "primeng/panel";
import { AppMainComponent } from "./pages/main/app.main.component";
import { AppTopBarComponent } from "./view/top-bar/app.topbar.component";
import { AppFooterComponent } from "./view/footer/app.footer.component";
import { MenuService } from "./service/app.menu.service";

describe('AppComponent', () => {
    beforeEach(async(() => {
        TestBed.configureTestingModule({
          imports: [
            NoopAnimationsModule,
            RouterTestingModule,
            AccordionModule,
            PanelModule,
          ],
          declarations: [
            AppComponent,
            AppMainComponent,
            AppTopBarComponent,
            AppFooterComponent,
          ],
          providers: [MenuService],
        }).compileComponents();
    }));
    it('should create the app', async(() => {
        const fixture = TestBed.createComponent(AppComponent);
        const app = fixture.debugElement.componentInstance;
        expect(app).toBeTruthy();
    }));
});
