import { UsersUiPage } from './app.po';

describe('users-ui App', () => {
  let page: UsersUiPage;

  beforeEach(() => {
    page = new UsersUiPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
