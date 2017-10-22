<div class="profile-hero-container">
  <div class="profile-hero-alignment">
    <img width="100" height="100"  src="https://ui-avatars.com/api/?rounded=true&size=200&background=e36f4a&color=fff&name=${user.first_name} ${user.last_name}" alt="">
    <div class="profile-user-container">
      <span class="profile-user-name">${user.first_name}</span>
      <span class="profile-user-created"><spring:message code="common.hero.title" arguments="${user.days_since_creation}" /></span>
    </div>
  </div>
</div>
<div class="profile-hero-catchphrase">
  <span>${hero_message}</span>
</div>
<div class="profile-hero-border"></div>
