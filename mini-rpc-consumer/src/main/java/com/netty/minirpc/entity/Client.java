package com.netty.minirpc.entity;

import com.netty.minirpc.entity.base.Component;
import com.netty.minirpc.entity.component.RegisterConfig;

public interface Client extends Component {

    void setRegisterConfig(RegisterConfig registerConfig);

}
