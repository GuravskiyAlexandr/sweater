insert into usr (id, username, password, active)
    values (1, 'admin', '$2a$08$B0Zih/A07ceAsvpJe9fxMeHleHGwGvrco3y9pRYdpbvO0cx0OKgkS', true);

insert into user_role (user_id, roles)
    values (1, 'USER'), (1, 'ADMIN');

-- update usr set password = '$2a$08$B0Zih/A07ceAsvpJe9fxMeHleHGwGvrco3y9pRYdpbvO0cx0OKgkS'
-- password = 11
-- https://www.devglan.com/online-tools/bcrypt-hash-generator